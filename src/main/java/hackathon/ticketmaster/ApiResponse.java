package hackathon.ticketmaster;

public class ApiResponse {
    int resultCount;
    ApiResult[] results;

    public int getResultCount() {
        return this.resultCount;
    }

    public ApiResult[] getResults() {
        return this.results;
    }
} // ApiResponse
