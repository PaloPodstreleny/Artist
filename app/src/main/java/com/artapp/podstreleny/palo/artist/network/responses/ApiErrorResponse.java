package com.artapp.podstreleny.palo.artist.network.responses;

public class ApiErrorResponse<T> extends ApiResponse<T> {

    private final String errorMessage;

    public ApiErrorResponse(String errorMessage){
        this.errorMessage = errorMessage;

    }

    public String getErrorMessage() {
        return errorMessage;
    }

}