FROM node:alpine
ARG NG_CLI_ANALYTICS="false"

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN npm install -g @angular/cli

RUN npm install

CMD ["ng", "serve", "--host", "0.0.0.0","--disable-host-check"]