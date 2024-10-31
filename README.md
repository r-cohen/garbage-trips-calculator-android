# garbage-trips-calculator-android
Android client app for calculating the minimum number of garbage trips.
This app consumes the [garbage-trips-calculator-api](https://github.com/r-cohen/garbage-trips-calculator-api)

## Debug Build
Available [here](https://github.com/r-cohen/garbage-trips-calculator-android/releases/tag/debug-alpha)

## Scalibility Considerations
1. The client application can hold a local cache, as a database for example,
where the requests and responses to the REST API can be saved. We would need to compare a list of Floats to compare with the previous requests.
This can be done by sorting the List before serializing it.
2. The REST API can also hold a cache on the server side, and the same way fetch the number of trips from a previously already calculated array of numbers.
3. If the size of the array of weights in request of the API is expected to become significant in size, it can also become a scale problem.
Not only the API request might timeout if the request is too big, but also the compute would take a long time to process.
Therefore, we can consider having a maximum size of weights in the request, and eventually sending multiple requests. The data structure would need to be slightly changed,
and we would need to add a session identifier to re-group all the requests on the server-side alomng with the total number of bags,
and on the server-side some sort of in-memory hash map to hold the pending requests previously received.

## Adding Database Persistence in Future versions

### On the client app side
We can use a [Room database](https://developer.android.com/training/data-storage/room) to hold the previously requests and response. The request can be the BagWeightsRequest
serialized as a String as long as the List<Float> is sorted, and this field in the Data Access Object can be a primary key, and the just the number of trips as an Integer for the 2nd field.

### On the REST API side
A similar database structure can be used on the server side in a Mongo DB database. The primary key would be the serialized sorted array of weights, and the value the output of the calculation
of the minimum number of trips. As a matter of fact, any key-value database engine can be used for the cache here. Another possibility, is to use an in-memory key-value dataset
for the cache (if we are not concerned about persisting it) by using something like Redis.
