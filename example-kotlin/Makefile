GRADLE_VERSION=5.2.1

GRADLEW=./gradlew

FAAS_SOURCE_DIR=faas
FAAS_DIST_DIR=build/faas
JAR_SOURCE_FILENAME=example-kotlin.jar
JAR_SOURCE_LOCATION=build/libs/$(JAR_SOURCE_FILENAME)
JAR_SINK_FILENAME=function.jar
JAR_SINK_LOCATION=$(FAAS_DIST_DIR)/$(JAR_SINK_FILENAME)


print-%: ; @echo $*=$($*)
guard-%:
	@test ${${*}} || (echo "FAILED! Environment variable $* not set " && exit 1)
	@echo "-> use env var $* = ${${*}}";

.PHONY : help
help : Makefile
	@sed -n 's/^##//p' $<



## gradle-wrapper:   : install gradle wrapper
gradle-wrapper:
	$(GRADLEW) --version
	$(GRADLEW) wrapper --gradle-version=$(GRADLE_VERSION)
	$(GRADLEW) --version

## clean
clean:
	$(GRADLEW) clean
	-rm -rf $(FAAS_DIST_DIR)

## build
build: clean
	$(GRADLEW) shadowJar
	mkdir -p $(FAAS_DIST_DIR)
	cp -rf $(FAAS_SOURCE_DIR)/** $(FAAS_DIST_DIR)/
	cp $(JAR_SOURCE_LOCATION) $(JAR_SINK_LOCATION)
	ls -la $(FAAS_DIST_DIR)
## start
start:
	cd $(FAAS_DIST_DIR) && func host start --port 7071

## up
up: build
	make start
