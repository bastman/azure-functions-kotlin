GRADLE_VERSION=5.2.1

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
	./gradlew clean


## build
build: clean
	./gradlew packageAzureFunction
	mkdir -p build/azure-functions
	cp build/libs/example-kotlin.jar build/azure-functions/function.jar
## start
start: build
	cd build/azure-functions
	func host start
	func host start

