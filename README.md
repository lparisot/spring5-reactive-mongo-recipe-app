# spring5-reactive-mongo-recipe-app

[![CircleCI](https://circleci.com/gh/lparisot/spring5-reactive-mongo-recipe-app.svg?style=svg)](https://circleci.com/gh/lparisot/spring5-reactive-mongo-recipe-app)
[![codecov](https://codecov.io/gh/lparisot/spring5-reactive-mongo-recipe-app/branch/master/graph/badge.svg)](https://codecov.io/gh/lparisot/spring5-reactive-mongo-recipe-app)

Reactive Recipe Application Using MongoDB

### Add project lombok

```
Add dependency:
    groupId: org.projectlombok
    artifactId: lombok

Add plugin
    In preferences/plugin, add lombok

In Preferences, Build/Compiler/Annotation processors, enable annotation processing
```

### Automatic reload when code modification

1. add spring-boot-devtools in your project
1. meta+shift+a
    1. select registry
    1. search for compiler.automake.allow.when.app.running
    1. set to true
1. open preferences
    1. in build/compiler, set build project automatically
1. add plugin livereload in the browser
