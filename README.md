## Введение
Проект elastic предназначен для тестирования использования OpenSearch для поиска и анализа данных.
## Запуск приложения
Для работы приложения необходимо наличие на компьютере Docker и запущенного Docker daemon. 
Инструкцию по подготовке и настройке локального окружения можно найти в [документации OpenSearch](https://opensearch.org/docs/latest/install-and-configure/install-opensearch/docker/)

Для запуска приложения необходимо перейти в корневую директорию в терминале и выполнить команду:
```bash
docker compose up
```
## Проверка запущенного приложения
После запуска контейнеров для проверки корректной работы необходимо выполнить команду в терминале:
```bash
 curl -XGET https://localhost:9200 -u 'admin:admin' --insecure
```
Запуск контейнеров прошел успешно, если в отет получен подобный json:
```bash
{
  "name" : "opensearch-node1",
  "cluster_name" : "opensearch-cluster",
  "cluster_uuid" : "Sysmxgt8QUia-SkQJYPDPw",
  "version" : {
    "distribution" : "opensearch",
    "number" : "2.19.1",
    "build_type" : "tar",
    "build_hash" : "2e4741fb45d1b150aaeeadf66d41445b23ff5982",
    "build_date" : "2025-02-27T01:16:47.726162386Z",
    "build_snapshot" : false,
    "lucene_version" : "9.12.1",
    "minimum_wire_compatibility_version" : "7.10.0",
    "minimum_index_compatibility_version" : "7.0.0"
  },
  "tagline" : "The OpenSearch Project: https://opensearch.org/"
}

```