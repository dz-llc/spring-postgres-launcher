To start the postgres docker container:

```bash
docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres
```

To start the elasticsearch docker container:

TODO: enable ssh requests to the elasticsearch container
```bash
docker run --name elasticsearch --net elastic -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e xpack.security.enabled=false -t docker.elastic.co/elasticsearch/elasticsearch:8.8.0
```