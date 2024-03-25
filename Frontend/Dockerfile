FROM node:20.11.0 as builder
WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
# 빌드가 실패하면 프로세스를 중지하고 종료
RUN npm run build || exit 1

FROM node:20.11.0
WORKDIR /app

COPY --from=builder /app/dist /app/dist

RUN npm install -g serve

EXPOSE 5173

ENTRYPOINT ["serve", "-l", "5173", "-s", "dist"]