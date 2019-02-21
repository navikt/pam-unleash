# Unleash support

Library that simplifies unleash usage and reduces the amount of code needed to use 
[Unleash](https://github.com/Unleash/unleash) feature toggles. Should make it 
easier to test feature toggled code, as well as cleaning up
toggles, because there are less dependencies passed around in client code

## Modules

The project is split in two modules, pam-unleash and pam-unleash-spring. 
* pam-unleash expose the implementation without depending on spring
* pam-unleash-spring makes it easy to use pam-unleash with spring 

### pam-unleash
Extends Unleash with an UnleashProvider that holds a singleton Unleash 
implementation. With this pattern it is not necessary to inject Unleash into 
the classes that uses it, making it easier to test and and clean up toggles
    
It also provides a slightly easier-to-use interface

In addition it contains a default implementation of the IsNotProd toggle strategy

#### Usage
Initialize the UnleashProvider by suppling an implemetation
```java
void initializeUnleash() {    
    Strategy[] strategies = { new IsNotProdStrategy() /* + any other strategies */ };
    
    UnleashConfig config = UnleashConfig.builder()
            .appName("Application name")
            .unleashAPI(unleashApiUrl)
            .build();
    
    UnleashProvider.initialize(new DefaultUnleash(config, strategies));
}
```

Using toggles can now be reduced to an import statement and the lines where unleash is
used

Example when testing if a toggle is disabled (given that
`import static no.nav.pam.unleash.UnleashProvider.toggle;`is imported)
```java
void improvedMethod() {
    if(toggle("new-feature").isDisabled()) {
        return;
    }
    // continue with other code
}
```

And of course the opposite
```java
if(toggle("new-feature").isEnabled()) {
    new FancyFeatureClass().theNewFeature();
}
```

Throwing exceptions of a toggle is disabled:
```java
toggle("new-feature").throwIfDisabled(() -> new UnnsupportedFeatureException());
```

You can also get the unleash object if you need something not covered above
```java
Unleash unleash = UnleashProvider.get();
if(!unleash.isEnabled("new-feature")) {
    throw new UnnsupportedFeatureException();
}
```

### pam-unleash-spring
Adds an easy-to-use annotations to enable unleash in spring and spring boot projects.
It is also possible to initialize a fake unleash for use locally or when testing

#### Requirements
Using the IsNotProd strategy requires either the `NAIS_CLUSTER_NAME` or `FASIT_ENVIRONMENT_NAME`
environment variables to be present unleass you supply them manually.
 
Unless you're using `fake = true`, you also need `${unleash.api.url}` (add this to your config) and either 
`${APP_NAME}` (should be provided when deploying with naisd) or 
`${NAIS_APP_NAME}` (should be provided when deploying with naiserator) 
to be resolveable in the environment. 


#### Usage
Annotate your configuration class with `@EnableUnleash`. This wil run the initialize the 
`UnleashProvider` as suggested earlier, and it should be ready to use.

It does require that some paramters are present

```java
@EnableUnleash
@Confiuration
class MyConfigration {
    // configuration/bean initialization
}
```

Test configuration. Every toggle defaults to disabled
```java
@EnableUnleash(fake = true)
@Confiuration
class MyConfigration {
    // configuration/bean initialization
}
```

Test configuration with some enabled toggles
```java
@EnableUnleash(fake = true, enabledFakeToggles = {"toggle1", "toggle3"})
```


