package com.myproject.springboot_advenced;

import com.myproject.springboot_advenced.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Arrays;

@RequiredArgsConstructor
@ShellComponent
public class CalculatorCommand {

    private final CalculatorService calculatorService;

    @ShellMethod(value = "Add Method Command", key = "add")
    public double addTwoNumber(
            @ShellOption(value = "-n") double a,
            @ShellOption(value = "-m") double b) {
        return calculatorService.add(a, b);
    }

    @ShellMethod(value = "Sum of Array", key = "sum")
    public double sum(@ShellOption(arity = 3) double[] nums) {
        return Arrays.stream(nums).sum();
    }




    @ShellMethod
    public double div(double a, double b) {
        return calculatorService.div(a, b);
    }

    @ShellMethod
    public double mul(double a, double b) {
        return calculatorService.mul(a, b);
    }

    @ShellMethod
    public double sub(double a, double b) {
        return calculatorService.sub(a, b);
    }


}
