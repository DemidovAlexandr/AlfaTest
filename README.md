# AlfaTest

## О проекте
Простое web-приложение, сравнивающее курс выбранной валюты с USD и отображающее соответствующую GIF-картинку

Запрос принимается на endpont /gif?currency, где currency - requestparam типа String *(код валюты, например "RUB")*

Также имеет index-page с формой отправки запроса

## Используемые технологии
* Java 11
* Spring Boot v2.6.1
  - Spring Web + ThymeLeaf
  - Spring Cloud (OpenFeign)
  - Spring RestDocs (MockMVC)
  - Spring Test + WireMock v2.27.2 + Hamcrest v2.1
* Lombok
* Gradle 
* Docker

## Запуск

### Варианты
1.  С помощью IDE
    - Клонировать репозиторий
    - Вызвать task gradle build
    - Запустить метод main
    
2. С помощью JDK с версией не ниже 11
   - Скачать AlfaTest-0.0.1-SNAPSHOT.jar из /build/libs
   - Запустить командную строку, перейти в директорию со скачанным .jar
   - Запустить программу из командой строки с помощью команды java -jar AlfaTest-0.0.1-SNAPSHOT.jar
   
3. Docker контейнер
   - docker pull andemidov/alfa_test
   - docker run -p 8080:8080 image_name
   
## Docker

Проект собран в docker image с помощью Dockerfile (лежит в root-директории проекта). 

[Ссылка на DockerHub](https://hub.docker.com/r/andemidov/alfa_test)

## Прочее
* Покрытие тестами составляет 81%
* Глобальные настройки приложения вынесены в application.properties
