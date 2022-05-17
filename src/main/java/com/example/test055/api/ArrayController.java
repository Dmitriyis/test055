package com.example.test055.api;

import com.example.test055.db.entity.ArrayTest;
import com.example.test055.dto.ArrayDto;
import com.example.test055.service.ArrayService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("main/array")
public class ArrayController {
    private final ArrayService arrayService;

    @GetMapping
    public ResponseEntity<?> getResult(@RequestParam("array1") String array1, @RequestParam("array2") String array2) {
        String[] resultSortedArray = arrayService.getResultSortedArray(array1, array2);
        System.out.println(array1);
        return ResponseEntity.ok().body(resultSortedArray);
    }

    @PostMapping
    public ResponseEntity<?> saveResult(@RequestParam String name, @RequestParam String result, @RequestParam String typeTask) {
        boolean isNullName = arrayService.getArrayTestByName(name) == null;

        if (isNullName) {
            arrayService.saveArrayTest(ArrayTest.builder().name(name).elementResult(result).typeTask(typeTask).build());
            return ResponseEntity.ok().body(ArrayDto.builder().name(name).result(result).typeTask(typeTask).build());
        } else {
            return ResponseEntity.badRequest().body("Имя занято");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteArrayResult(@RequestParam String name) {
        boolean isNullName = arrayService.getArrayTestByName(name) == null;

        if (!isNullName) {
            arrayService.deleteByName(name);
            return ResponseEntity.ok().body(true);
        } else {
            return ResponseEntity.badRequest().body("Такого имени нет");
        }
    }

    @PostMapping("/file")
    public ResponseEntity<?> saveResultArrayFile(@RequestParam String name, @RequestParam String result, @RequestParam String typeTask) {
        File[] arrayResultFiles = new File("arrayResultFile").listFiles();
        List<String> list = new ArrayList<>();

        for (File str : arrayResultFiles) {
            if (arrayService.getResultSortedArray(name, String.valueOf(str)).length >= 1) {
                list.add(name);
                break;
            }
        }

        if (list.isEmpty()) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("arrayResultFile" + "\\" + "arrayResult" + name + "-" + LocalDate.now() + "txt"))) {
                String text = name + "\n" + result + "\n" + typeTask;
                bw.write(text);
                return ResponseEntity.ok().body(ArrayDto.builder().name(name).result(result).typeTask(typeTask).build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return ResponseEntity.badRequest().body("Такое имя уже есть");
        }
    }

    @GetMapping("/result")
    public ResponseEntity<?> getArrayResult(@RequestParam String name) {
        boolean isNullName = arrayService.getArrayTestByName(name) == null;

        if (!isNullName) {
            ArrayTest arrayTestByName = arrayService.getArrayTestByName(name);
            return ResponseEntity.ok().body(ArrayDto.builder()
                    .name(arrayTestByName.getName())
                    .typeTask(arrayTestByName.getTypeTask())
                    .result(arrayTestByName.getElementResult()).build());
        } else {
            return ResponseEntity.badRequest().body("Такого имени нет");
        }
    }

    @GetMapping("/allResult")
    public ResponseEntity<?> getAllResult() {
        List<ArrayDto> arrayDto = arrayService.getAllArrayTest().stream().map(arrayTest -> ArrayDto.builder()
                .name(arrayTest.getName())
                .result(arrayTest.getElementResult())
                .typeTask(arrayTest.getTypeTask()).build()).collect(Collectors.toList());
        return ResponseEntity.ok().body(arrayDto);
    }

    @GetMapping("/listFileResult")
    public ResponseEntity<?> getAllFileResult() {
        List<String> arrayResultFile = Arrays.stream(new File("arrayResultFile")
                .listFiles()).map(pathFile -> String.valueOf(pathFile).substring(16)).collect(Collectors.toList());

        return ResponseEntity.ok().body(arrayResultFile);
    }

    @SneakyThrows
    @GetMapping("/fileResult")
    public ResponseEntity<?> getFileResult(@RequestParam String pathFile) {
        ArrayDto arrayDto = new ArrayDto();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("arrayResultFile" + "\\" + pathFile))) {
            while (bufferedReader.ready()) {

                boolean isTrue = true;
                int count = 1;

                String name = null;
                String result = null;
                String typeTask = null;

                while (isTrue) {
                    String el = bufferedReader.readLine();
                    switch (count) {
                        case 1 -> name = el;
                        case 2 -> result = el;
                        case 3 -> typeTask = el;
                    }

                    if (count == 3) {
                        isTrue = false;

                    }
                    count++;
                }
                arrayDto = ArrayDto.builder().name(name).result(result).typeTask(typeTask).build();
            }
        }
        return ResponseEntity.ok().body(arrayDto);
    }
}