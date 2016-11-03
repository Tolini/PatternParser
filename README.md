# PatternParser
A standalone java library for parsing string form data using interchangable self created patterns.

## Getting Started
To start using the library download the project by either cloning the repository or downloading it as a zip file.
Once you've got the files you can either use the source files in your project by adding them to your sources or you can use the jar file by adding it as a dependency.

### Using the library
Once you've got the library added to your project it is ready to go.
To parse input you need to start off with creating a TokenPattern that fits your needs, you can read about TokenPatterns [here](#token-pattern).
Then you feed the PatternParser class the TokenParser and your input like this:
```java
TokenPattern myTokenPattern = new TokenPattern("{ResultOne:String} {ResultTwo:String} {ResultThree:String}");
ResultPacket result = PatternParser.parse("some input string", myTokenPattern);
```
The result of the above code will be a ResultPacket containing the three strings `"some"`, `"input"` and `"string"` obtainable by the keywords `"ResultOne"`, `"ResultTwo"` and `"ResultThree`. You can read more about how the ResultPacket works [here](#result-packet)

## How it all works

### Token

### TokenPattern

### TokenParser

### Result Packet

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
