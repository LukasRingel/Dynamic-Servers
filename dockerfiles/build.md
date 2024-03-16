# Build an image

```bash
docker build -t <registry>/<image-name><tag> .
docker build -t localhost:5000/paper:1.xx.x .
docker build -t localhost:5000/velocity:x.x.x .
```

# Deploy an image
````bash
docker login <host>
docker push <registry>/<image-name><tag>

docker push localhost:5000/paper:1.
docker push localhost:5000/velocity:
````