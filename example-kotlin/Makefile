GRADLE_VERSION=5.2.1

GRADLEW=./gradlew
AZURE_FUNC_DIST_DIR=build/azure-functions
JAR_SOURCE_FILENAME=example-kotlin.jar
JAR_SOURCE_LOCATION=build/libs/$(JAR_SOURCE_FILENAME)
JAR_SINK_FILENAME=function.jar
JAR_SINK_LOCATION=$(AZURE_FUNC_DIST_DIR)/$(JAR_SINK_FILENAME)


print-%: ; @echo $*=$($*)
guard-%:
	@test ${${*}} || (echo "FAILED! Environment variable $* not set " && exit 1)
	@echo "-> use env var $* = ${${*}}";

.PHONY : help
help : Makefile
	@sed -n 's/^##//p' $<



## gradle-wrapper:   : install gradle wrapper
gradle-wrapper:
	./gradlew --version
	./gradlew wrapper --gradle-version=$(GRADLE_VERSION)
	./gradlew --version

## clean
clean:
	$(GRADLEW) clean
	-rm -rf $(AZURE_FUNC_DIST_DIR)

## build
build: clean
	$(GRADLEW) packageAzureFunction
	mkdir -p $(AZURE_FUNC_DIST_DIR)
	cp $(JAR_SOURCE_LOCATION) $(JAR_SINK_LOCATION)
	cp -rf host.json $(AZURE_FUNC_DIST_DIR)/
	cp -rf local.settings.json $(AZURE_FUNC_DIST_DIR)/
	ls -la $(AZURE_FUNC_DIST_DIR)
## start
start: build
	cd $(AZURE_FUNC_DIST_DIR)
	func host start


