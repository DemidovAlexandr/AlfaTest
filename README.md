# AlfaTest

## О проекте
Простое web-приложение, сравнивающее курс выбранной валюты с USD и отображающее соответствующую GIF-картинку

Принимает запросы на endpont  `/gif?currency`, где currency - requestparam типа String *(код валюты, например "RUB")*

Также имеет index-page с формой отправки запроса

## Используемые технологии
* Java 11
* Spring Boot v2.6.1
  - Spring Web + ThymeLeaf
  - Spring Cloud (OpenFeign)
  - Spring RestDocs (MockMVC)
  - Spring Test (JUnit 5, MockMVC) + WireMock v2.27.2 + Hamcrest v2.1
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

## Использование
По-умолчанию, приложение запускается на `http://localhost:8080/`

При переходе по `/` будет отображена приветственная страница, где можно ввести код валюты для сравнения, либо сразу сравнить RUB

Endpont `/gif?currency`принимает параметр currency с кодом валюты, отображает гифку и значения курсов валют за вчера и сегодня

Настройка основных переменных приложения может быть осуществлена через application.properties файл

## Docker

Проект собран в docker image с помощью Dockerfile (лежит в root-директории проекта). 

[Ссылка на DockerHub](https://hub.docker.com/r/andemidov/alfa_test)

## Прочее
* Покрытие тестами составляет 81%
* Бизнес-логика вынесена в отдельный слой сервисов
* Реализованы ExceptionHandlers для контроллера
