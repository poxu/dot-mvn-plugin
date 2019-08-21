# Dot Mvn Plugin for Intellij Idea

Plugin to integrate Intellij Idea with jvm.config file
from .mvn directory. maven.config on the way )) .

Watches jvm.config file and puts it's content to Build Tools -> Maven -> Runner -> VM Options
and to Build Tools -> Maven -> Importing -> Vm options for importer

## Issues

### Vm options for importer are not project specific

Vm options for importer are supposed to be project specific, 
but currently they are not. Vote https://youtrack.jetbrains.com/issue/IDEA-186971 if
you want the situation to change.