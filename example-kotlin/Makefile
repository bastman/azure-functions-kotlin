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
	-cp $(FAAS_SOURCE_DIR)/default.settings.json $(FAAS_SOURCE_DIR)/local.settings.json
	$(GRADLEW) shadowJar
	mkdir -p $(FAAS_DIST_DIR)
	cp -rf $(FAAS_SOURCE_DIR)/** $(FAAS_DIST_DIR)/
	cp $(JAR_SOURCE_LOCATION) $(JAR_SINK_LOCATION)
	-cp $(FAAS_SOURCE_DIR)/local.settings.json $(FAAS_DIST_DIR)/
	ls -la $(FAAS_DIST_DIR)
## start
start:
	-cp $(FAAS_SOURCE_DIR)/default.settings.json $(FAAS_DIST_DIR)/local.settings.json
	cd $(FAAS_DIST_DIR) && func host start --port 7071

## up
up: build
	make start

FAAS_API_BASE_URI=http://localhost:7071/api
FAAS_CURL=curl
## curl-api.verbose: curl -v ...
curl-api.verbose:
	make curl-api FAAS_CURL="curl -v"
## curl-api
curl-api:
	$(FAAS_CURL) $(FAAS_API_BASE_URI)/pingV1
	@echo ""
	$(FAAS_CURL) $(FAAS_API_BASE_URI)/pingV2
	@echo ""
	$(FAAS_CURL) $(FAAS_API_BASE_URI)/fooV1
	@echo ""
	$(FAAS_CURL) --request POST -d ""{\"foo\":\"bar\"}"" $(FAAS_API_BASE_URI)/echoV1?name=123
	@echo ""
	$(FAAS_CURL) --request POST  -d ""{\"foo\":\"bar\"}"" $(FAAS_API_BASE_URI)/echoV2?name=123
	@echo ""
	$(FAAS_CURL) --request POST  -d ""{\"foo\":\"bar\"}"" $(FAAS_API_BASE_URI)/runV1?name=123
	@echo ""
	$(FAAS_CURL) --request POST $(FAAS_API_BASE_URI)/tikaV1 \
		-H 'accept:n' \
		-H 'cache-control: no-cache'   \
		-H 'content-type: application/octet-stream' \
		--data-binary @$(PWD)/example-kotlin/src/test/resources/TikaTestDocument.pdf
	@echo ""
	$(FAAS_CURL) --request POST $(FAAS_API_BASE_URI)/tikaV2 \
		-H 'accept:n' \
		-H 'cache-control: no-cache'   \
		-H 'content-type: application/octet-stream' \
		--data-binary @$(PWD)/example-kotlin/src/test/resources/TikaTestDocument.pdf
	@echo ""