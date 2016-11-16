# PatternParser
A standalone java library for parsing string form data using interchangable self created patterns.

## Getting Started
To start using the library download the project by either cloning the repository or downloading it as a zip file.
Once you've got the files you can either use the source files in your project by adding them to your sources or you can use the jar file by adding it as a dependency.

### Using the library
Once you've got the library added to your project it is ready to go.
To parse input you need to start off with creating a TokenPattern that fits your needs, you can read about TokenPatterns [here](#tokenpattern).
Then you feed the PatternParser class the TokenParser and your input like this:
```java
TokenPattern myTokenPattern = new TokenPattern("{ResultOne:String} {ResultTwo:String} {ResultThree:String}");
ResultPacket result = PatternParser.parse("some input string", myTokenPattern);
```
The result of the above code will be a ResultPacket containing the three strings `"some"`, `"input"` and `"string"` obtainable by the keywords `"ResultOne"`, `"ResultTwo"` and `"ResultThree`. You can read more about how the ResultPacket works [here](#resultpacket)

## How it all works
The library consists of 4 parts; the TokenPattern, the TokenParser, the ResultPacket and lastly the PatternParser that ties it all together. This section will explain what each of these parts are and how they work.

### What is a Token?
In this library a Token in an object that tells the PatternParser which kind of data needs to be parsed from the input.
The Token consists of 3 parts and looks like this: 
```java
"{Name:Type}EndMarker"
```
It is used only as a part of a TokenPattern.
#### The Name
The name of a Token is used to save the parsed data in the ResultPacket, thus making it so you get the information from the ResultPacket by using the name of the Token.

#### The Type
The type of the Token is the type of data that needs to be parsed, ie. which type of TokenParser needs to be used to parse the data and which type of data to be stored in the ResultPacket.

#### The EndMarker
The EndMarker is an optional part of the Token that may be used to give the TokenPattern a more meaningful syntax such as spaces or other special characters between Tokens, or to always have the number 42 follow a Token. They are used be the TokenParsers which in case of a non empty EndMarker most likely will parse much easier.

**Note: It is not advised to have an EndMarker that might possibly be part of the input that the Token is ment to parse, ie. a number if the Token Type is any kind of numeric type, this might cause the Parser fail.**

### TokenPattern
A TokenPattern is an object that consists of one or more Tokens in a specific ordering. It is used to determine which TokenParsers to use at which points in the input. The PatternParser will go through the TokenPattern, one Token at a time, and invoke the corresponding TokenParser on the input, once a TokenParser is done parsing the PatternParser will move on to the next TokenParser and tell it to parse the remainder of the input. This makes it crutial that the input, that one needs parsed, looks exacttly like the TokenPattern.
However you can have as many TokenPatterns as you want so you are not limited to one kind of input.
To create a TokenPattern you simply instantiate the object and supply it with a string corresponding to the pattern.

**Example:**
```jave
TokenPattern myPattern = new TokenPattern("{Name1:Type}EndMarker{Name2:Type}EndMarker{Name3:Type}EndMarker");
```
**Note: The pattern can have as many tokens as you want but each token needs to have it's own unique name otherwise the data in the ResultPacket might get overwritten.**
### TokenParser
A TokenParser is a function that parses a specific type of token. It takes the input, the Token and the ResultPacket as it's parameters.
The library comes with five TokenParsers:
* Digit, which parses a single digit 0-9.
* Number, which parses an integer.
* Decimal, which parses a precision number fx. 10.5. Note that the punctuation mark is used as the decimal seperator.
* Char, which parses a single alphabetic character.
* String, which parses a string. Note that if no EndMarker is present the string will be split on a space unless it is contained by quotation marks `""`.

If the default parsers are not wanted they can be overwritten by added a new parser of the same type or to disable them all simply invoke the static function `PatternParser.shouldLoadDefaultParsers(false);`.

**Warning: Some of the default TokenParsers may not be optimized for large data and thus relying on them for parsing large inputs may result in slow running times**

#### Overwritting or adding your own TokenParser
To overwrite or add you own TokenParser invoke the static function `PatternParser.addTokenParser("Type", parsingFunction);`
The two parameters are the type of the TokenParser, ie. which Token Type it will be invoked on, and the actual function that parses the input. The function can either be a real function or a lambda expression.

##### ParsingFunction guidelines
In order for the PatternParser to work all ParsingFunctions must return an integer value corresponding to the number of characters the function has parsed, fx. if a function to parse an integer parses the integer 100 the function should return the integer 3.
Furthermore it is advised to take advantage of the EndMarker if the Token has one, the EndMarker makes it so you don't have to manually search for the potential end of the input.
Lastly the ParsingFunction is responsible for saving it's parsed data to the ResultPacket by calling on of the `ResultPacket.add()` functions on the supploed ResultPacket.
**Note: The default parsers do not work if the token has a EndMarker but the input doesn't, however this does not have to be the case for custom parsers.**

### ResultPacket
The ResultPacket is an object used to store all the parsed data from the TokenParsers. The data is stored in a map with the key being the name of the Token thus allowing for the data to be retrieved using one of the `ResultPattern.get("key")`functions with `key` being the name of the Token that was used to parse the input. For example the input parsed with the Token `{MyData:Type}EndMarker` is stored at the key `MyData` and can be retrieved using the approriate `ResultPacket.get("MyData")` function.

## License
This project is licensed under the GPL-3.0 License - see the [LICENSE](LICENSE) file for details
