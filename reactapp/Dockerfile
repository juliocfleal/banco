FROM node:current-alpine3.17

COPY . .
RUN apk add --no-cache bash
RUN npm install express
ENTRYPOINT [ "node", "server.js"]