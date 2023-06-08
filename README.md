To start the postgres docker container:

```bash
docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres
```

To start the elasticsearch docker container:
```shell
docker-compose up -d
```