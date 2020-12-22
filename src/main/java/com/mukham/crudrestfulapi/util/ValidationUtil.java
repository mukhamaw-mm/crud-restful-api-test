package com.mukham.crudrestfulapi.util;

import com.mukham.crudrestfulapi.model.request.OrderRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static boolean isValidString(String input){
        boolean b = true;
        if(input == null || input.trim().equals("")){
            b=false;
        }
        return b;
    }

    public static boolean validate(OrderRequest request) {


        if (!ValidationUtil.isValidString(request.getCustomerName()) ||
                !ValidationUtil.isValidString(request.getCustomerAddress()) ||
                !ValidationUtil.isValidString(request.getCustomerPhone()) ||
                !ValidationUtil.isValidString(request.getCustomerEmail())) {
            return false;
        }

        if (request.getCustomerPhone().length() < 5 || request.getCustomerPhone().length() > 13) {
            return false;
        }

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.getCustomerEmail());

        if (!matcher.matches()) {
            return false;
        }

        return true;
    }
}
