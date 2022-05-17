#URL для Postman

    1. localhost:8080/main/array 
        В Query Params указываем array1 это substrings
        array2 это кондидаты на поиск substrings.
    Возрощает substrings которые были найдены в array2.
    GET

---
    2. localhost:8080/main/array 
    В Query Params указываем name, result, typeTask
    данный URL сохроняет результат URL localhost:8080/main/array.
    POST
--- 

    localhost:8080/main/array 
    В Query Params указываем name, удаления рузьтатов
    по name.
    DELETE

--- 

    localhost:8080/main/array/file
    В Query Params указываем name, result, typeTask
    Данный URL сохраняет результат URL localhost:8080/main/array В файл.
    POST

---

    localhost:8080/main/array/result
    В Query Params указываем name. Поиск по имени.
    GET

---

    localhost:8080/main/array/allResult
    Поиск всех записей в БД.

---

    localhost:8080/main/array/listFileResult
    Поиск всех записей в файле 

---
    
    localhost:8080/main/array/fileResult 
    В Query Params указываем path путь до файла. 
    Путь к файлам можно посмотреть в URL localhost:8080/main/array/listFileResult
