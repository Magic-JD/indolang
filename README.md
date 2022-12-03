# Indolang

## Indonesian english dictionary/learning tool

This microservice currently does 3 things using an internal dictionary:

1) It can return any matching words in the opposite language if english or indonesian words are given for it.
2) Users can add new words to the dictionary.
3) Users can test themselves using words from the dictionary.

It requires the user to first login and use basic auth. Passwords are hashed and stored in the database. Username and
password will be required to call any of the endpoints other than registration.

The majority of the endpoints need the 'accept language' header to be populated with the user's language. If there was a
front end for this microservice this would be done there. If the user selects english then the endpoints will be english
focused (e.g. if their 'accept-language' header was _en_ they would be expected to give an english word to the lookup
endpoint).

The two languages don't have to be english and indonesian. If you change the application properties to two other
languages you can populate your own database with words in that language. However currently this application only
supports two languages, a third can not be supported by the current database schema.

## Endpoints

### Registration

    /registration

You will have to call this endpoint first to register to the service. This will save your username and hashed password,
and then you can use this as a basic auth to access the rest of the site.

This is a post endpoint which takes this :

    {
        "username": <username>,
        "password": <password>
    }


### Lookup

    /lookup/{word}

This endpoint will look up the word that is given and return you a list of translations. It will assume the word is in
the language given on your 'accept-language' header.

It will return a response with this form:

    {
        "word": "rope",
        "wordDefinitions": [
            "tali"
        ]
    }

### Question

    /question

This get call returns a word in the language given in the 'accept-language' header. This word will then be stored in the
learner database. The user should seek to answer the words with the correct translation.

The response will be in this form:

    {
        "word": "accent"
    }

If the user has words they are already being tested on then a word from that database is given to the user. If the user
has not, or they have been too recently tested on these words then a new word is taken from the word database. The
current question will be re-shown to the user after 5 minutes if they get it wrong, and after four hours if they get it
right once, increasing exponentially for each subsequent right answer. If they get a question wrong it resets, and they
will have to answer it again after 5 minutes.

    /answer

This is accessed through a post request which requires an input in the form:

    {
        "askedQuestion": <The word given by the question endpoint>,
        "answer": <The correct answer to that question>
    }

This will then return a response in this style:

    {
        "pass": <true, false>,
        "word": <The word the user tried to translate>,
        "submittedTranslation": <The translation that the user submitted>
        "translations":[<A list of all correct translations>]
    }

Which will allow the front end to format the correct response to the user, either telling them they are correct or that
they are wrong, and they should have chosen one of the correct translations.

### Update

    /update

This endpoint is a post that lets you add a new word to the database. It takes an input in the form:

    {
        "word": <The word in the language of the 'accept-language' header>,
        "translation": <The translation of that word in the other language>
    }

This will either create a new entry in the database with the word and the translation, or if the word is already in the
database it will add the translation to the list of translations.

## Database

There are three tables that are used for this project, using a mongodb database.

### Users

| _id      | username | password        | enabled | roles |
|----------|----------|-----------------|---------|-------|
| ObjectId | String   | String (hashed) | boolean | Array |

This table is used to store information about the user such as what roles they have in order to allow or deny them
access to certain endpoints.

### word_translations

| _id      | locale                               | keyWord | translations     |
|----------|--------------------------------------|---------|------------------|
| ObjectId | String<br/> The language of the word | String  | Array of Strings |

This table holds the information about the individual words. It is indexed on the keyWord, as it is usually queried on
the word directly and there are unlikely to be duplicates in another language.

### learner

| _id      | wordTranslations | username | date             | successfulAnswers |
|----------|------------------|----------|------------------|-------------------|
| ObjectId | ObjectId         | String   | Array of Strings | int               |

This table hold the information about the learner and the questions that they have already answered. The
wordTranslations are stored as a document reference, so they can be quickly retrieved from the object. The data is
indexed using the username, as each user would likely have a small grouping of answers, making this the ideal key to
reduce on lookup time.

## Notes:

- Additional contributor (floorboardMan) is me committing with a device linked to another account.
- The application.properties file has been replaced with an example file which shows the structure without showing my
  password.
- If you want to run this at home you will have to create a mongodb and link it yourself. As there will be no words you
  will also have to populate the wordlist yourself using the update endpoint.
