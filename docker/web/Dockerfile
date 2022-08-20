FROM azulzulu-openjdk11 as build

RUN apt-get update && apt-get -y install curl

WORKDIR work
COPY mill build.sc .
ADD common .common
ADD tools .tools
ADD web .web

RUN ls -la
RUN .mill 'web.assembly'

FROM azulzulu-openjdk11

RUN mkdir -p opttensil
COPY --from=build workoutwebassembly.destout.jar opttensilweb.jar

CMD java -jar opttensilweb.jar
