FROM oven/bun:latest AS build

WORKDIR /app

COPY ./docs/package.json ./docs/package.json

COPY ./docs ./docs

RUN cd docs && bun install

COPY ./library ./library
COPY ./examples ./examples

RUN cd docs && bun run /prepare.ts

RUN cd docs && bun run build

# CMD ["sleep", "infinity"]

FROM busybox:latest AS runtime
COPY --from=build /app/docs/build /www
EXPOSE 80
CMD ["httpd", "-f", "-v", "-p", "80", "-h", "/www"]
