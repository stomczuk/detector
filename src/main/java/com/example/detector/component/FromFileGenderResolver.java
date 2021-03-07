package com.example.detector.component;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import java.io.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static com.example.detector.constant.ExceptionMessageConstant.JAR_FILE_NOT_FOUND;
import static com.example.detector.constant.FileConstant.*;
import static com.example.detector.enums.EnumGender.*;
import static com.example.detector.enums.EnumVariant.ALL;
import static com.example.detector.enums.EnumVariant.ONE;


@Component
public class FromFileGenderResolver implements GenderResolver {

    final static String WHITESPACE_REGEX = "\\s+";
    final static String LINE_SEPARATOR_REGEX = "line.separator";

    @Override
    public String detectGender(String name, String variant) throws IOException {
        String gender;
        if (isVariantOne(variant)) {
            gender = detectGenderOneToken(name);
        } else if (isVariantAll(variant)) {
            gender = detectGenderAllToken(name);
        } else gender = INCONCLUSIVE.name();
        return gender;
    }

    private boolean isVariantAll(String variant) {
        return StringUtils.equalsIgnoreCase(variant, ALL.name());
    }

    private boolean isVariantOne(String variant) {
        return StringUtils.equalsIgnoreCase(variant, ONE.name());
    }

    @Override
    public StreamingResponseBody getAllNameTokensForGender(String gender)  {
        if (isMale(gender)) {
            return getNameTokensFromJar(PATH_MALE_LIST);
        } else if (isFemale(gender)) {
            return getNameTokensFromJar(PATH_FEMALE_LIST);
        }
        return null;
    }

    private boolean isMale(String gender) {
        return StringUtils.equalsIgnoreCase(gender, MALE.name());
    }

    private boolean isFemale(String gender) {
        return StringUtils.equalsIgnoreCase(gender, FEMALE.name());
    }

    private String isolateFirstName(String name) {
        String[] splittedName = splitName(name);
        return splittedName[0];
    }

    private String[] splitName(String name) {
        String[] splitName = name.split(WHITESPACE_REGEX);
        return splitName;
    }

    private String detectGenderBaseOnCounters(int maleCounter, int femaleCounter, int inconclusiveCounter) {
        if (inconclusiveCounter > 0) {
            return INCONCLUSIVE.name();
        } else if (maleCounter > femaleCounter) {
            return MALE.name();
        } else if (femaleCounter > maleCounter) {
            return FEMALE.name();
        }
        return INCONCLUSIVE.name();
    }

    private String detectGenderOneToken(String name) throws IOException {
        String firstName = isolateFirstName(name);
        if (isMaleName(firstName, PATH_MALE_LIST)) {
            return MALE.name();
        } else if (isFemaleName(firstName, PATH_FEMALE_LIST)) {
            return FEMALE.name();
        }
        return INCONCLUSIVE.name();
    }

    private String detectGenderAllToken(String name) throws IOException {
        int maleCounter = 0;
        int femaleCounter = 0;
        int inconclusiveCounter = 0;
        String[] names = splitName(name);
        for (String n : names) {
            if (isMaleName(n, PATH_MALE_LIST)) {
                maleCounter++;
            } else if (isFemaleName(n, PATH_FEMALE_LIST)) {
                femaleCounter++;
            } else inconclusiveCounter++;
        }
        return detectGenderBaseOnCounters(maleCounter, femaleCounter, inconclusiveCounter);
    }

    private boolean isFemaleName(String name, String listName) throws IOException {
        return isNameInNameTokensList(name, listName);
    }

    private boolean isMaleName(String name, String listName) throws IOException {
        return isNameInNameTokensList(name, listName);
    }

    private boolean isNameInNameTokensList(String name, String listName) throws IOException {
        BufferedReader nameTokens = loadNameTokensFromJar(listName);
        String nameToken;
        while ((nameToken = nameTokens.readLine()) != null) {
            if (StringUtils.equalsIgnoreCase(name, nameToken)) {
                return true;
            }
        }
        nameTokens.close();
        return false;
    }


    private BufferedReader loadNameTokensFromJar(String listName) throws FileNotFoundException {
        try {
            JarFile jarFile = new JarFile(PATH_NAMES_JAR);
            JarEntry entry = jarFile.getJarEntry(listName);
            InputStream input = jarFile.getInputStream(entry);
            InputStreamReader isr = new InputStreamReader(input);
            return new BufferedReader(isr);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(JAR_FILE_NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public StreamingResponseBody getNameTokensFromJar(String listName) {
        StreamingResponseBody stream = out -> {
            BufferedReader nameTokens = loadNameTokensFromJar(listName);
            String nameToken;
            while ((nameToken = nameTokens.readLine()) != null) {
                out.write(nameToken.getBytes());
                out.write(System.getProperty(LINE_SEPARATOR_REGEX).getBytes());
                out.flush();
            }
            nameTokens.close();
            out.close();
        };
        return stream;
    }
}
