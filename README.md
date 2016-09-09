# distributed-filestorage
Exercise in exploring large file storage in memcached

Created memcached container using:
```
docker run --name memcached -d -p 9001:11211 memcached memcached -m 1000
```

Created test file using:
```
dd if=/dev/urandom of=bigoldfile.dat bs=1048576 count=250
```
