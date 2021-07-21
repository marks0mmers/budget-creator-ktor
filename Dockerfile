FROM gradle AS build
COPY --chown=gradle:gradle ./src /home/gradle/src/src
COPY --chown=gradle:gradle ./build.gradle.kts /home/gradle/src/build.gradle.kts
COPY --chown=gradle:gradle ./settings.gradle.kts /home/gradle/src/settings.gradle.kts
WORKDIR /home/gradle/src
RUN gradle installDist --no-daemon

FROM adoptopenjdk/openjdk14
EXPOSE 8000:8000
RUN mkdir /app
COPY --from=build /home/gradle/src/build/install/budget-creator-ktor/ /app/
WORKDIR /app/bin
CMD ["./budget-creator-ktor"]
