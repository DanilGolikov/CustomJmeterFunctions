package com.xmeter.functions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.functions.AbstractFunction;
import org.apache.jmeter.functions.InvalidVariableException;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterVariables;

import static java.lang.Math.*;

public class generateINN_natural extends AbstractFunction{
    private static final List<String> desc = new LinkedList<String>();
    private Object[] values; // The value of the passed parameter
    Random rd = new Random();
    private static final String MyFunctionName = "__generateINN_natural"; //function name

    public int randomFunc(int min, int max)
    {
        return rd.nextInt((max-min) + 1) + min;
    }


    static {
        desc.add("Name of variable in which to store the result (optional)");
    }

    @Override
    public List<String> getArgumentDesc() {
        return desc;
    }


    @Override
    public String execute(SampleResult arg0, Sampler arg1) throws InvalidVariableException {

        int[] region = {0,0};
        int[] inspection = {0, 0};
        int[] num = {0,0,0,0,0,0};
        int[] kontr = {0,0};
        int i = 0;

        while (region[0] == 0 && region[1] == 0)
        {
            region[0] = abs(randomFunc(0, 9));
            region[1] = abs(randomFunc(0, 9));
        }

        while(inspection[0] == 0 &&  inspection[1] == 0)
        {
            inspection[0] = abs(randomFunc(0, 9));
            inspection[1] = abs(randomFunc(0, 9));
        }

        for(i=0;i<6;i++) num[i] = abs(randomFunc(0, 9));

        kontr[0] = ((7*region[0]+
                2*region[1]+
                4*inspection[0]+
                10*inspection[1]+
                3*num[0]+
                5*num[1]+
                9*num[2]+
                4*num[3]+
                6*num[4]+
                8*num[5])%11)%10;

        kontr[1] = ((3*region[0]+
                7*region[1]+
                2*inspection[0]+
                4*inspection[1]+
                10*num[0]+
                3*num[1]+
                5*num[2]+
                9*num[3]+
                4*num[4]+
                6*num[5]+
                8*kontr[0])%11)%10;

        //if (kontr[0]==10) kontr[0]=0;

        String result = String.format("%d%d%d%d%d%d%d%d%d%d%d%d", region[0], region[1],inspection[0],inspection[1],num[0],num[1],num[2],num[3],num[4],num[5],kontr[0],kontr[1]);


        if (values.length > 0)
        {
            JMeterVariables vars = getVariables();
            String userVariable = ((CompoundVariable) values[0]).execute().trim();
            vars.put(userVariable, result);
        }
        return result;
    }

    @Override
    public String getReferenceKey() {
        return MyFunctionName;
    }

    @Override
    public void setParameters(Collection<CompoundVariable> parameters) throws InvalidVariableException {
        values = parameters.toArray();
    }
}