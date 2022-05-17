package com.example.test055.service;

import com.example.test055.db.entity.ArrayTest;
import com.example.test055.db.repository.ArrayTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArrayService {

    private final ArrayTestRepository arrayTestRepository;

    @Transactional
    public ArrayTest saveArrayTest(ArrayTest arrayTest) {
        return arrayTestRepository.save(arrayTest);
    }

    public ArrayTest getArrayTestByName(String name) {
        return arrayTestRepository.findByName(name).orElse(null);
    }

    @Transactional
    public long deleteByName(String name) {
        return arrayTestRepository.deleteByName(name);
    }

    public List<ArrayTest> getAllArrayTest() {
        return arrayTestRepository.findAll();
    }


    public String[] getResultSortedArray(String array1, String array2) {

        String[] array1Local = Arrays.stream(array1.split(" ")).map(array -> array.trim()
                .toLowerCase()).filter(arrayLen -> arrayLen.length() >= 1).toArray(String[]::new);

        String[] array2local = Arrays.stream(array2.split(" ")).map(array -> array.trim()
                .toLowerCase()).filter(arrayLen -> arrayLen.length() >= 1).toArray(String[]::new);

        return Arrays.stream(array1Local).map(array11 -> {
            return Arrays.stream(array2local).map(array22 -> searchSubString(array11.split(""), array22.split("")))
                    .distinct().filter(Objects::nonNull).collect(Collectors.joining());
        }).filter(lens -> lens.length() >= 1).toArray(String[]::new);

    }

    private String searchSubString(String[] array1, String[] array2) {

        for (int j = 0; j < array2.length; j++) {

            if (array2[j].equals(array1[0])) {
                String arrayToString = String.join("", array1);
                String candidateResultString = Arrays.stream(array2).skip(j).limit(array1.length).collect(Collectors.joining());
                return candidateResultString.equals(arrayToString) ? candidateResultString : null;
            }
        }

        return null;
    }

}
