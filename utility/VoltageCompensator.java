package org.firstinspires.ftc.teamcode.mollusc.utility;

import org.firstinspires.ftc.teamcode.mollusc.utility.PIDF;
import org.firstinspires.ftc.teamcode.mollusc.Mollusc;

import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class VoltageCompensator {

    public static final double MAX_VOLTAGE = 14.0;

    private DcMotorEx motor;
    private PIDF pidf;
    private double maxCurrent;
    private Filter.LowPass filter = new Filter.LowPass(0, 0.5);

    public VoltageCompensator(DcMotorEx motor, PIDF pidf, double maxCurrent) {
        this.motor = motor;
        this.pidf = pidf;
        this.maxCurrent = maxCurrent;
    }

    // Returns a feed-forward value plus the original power.
    public double adjustPower(double power, double voltage) {
        /*
        P = V * I --> Power = Voltage * Current
        Let's use a simplified model where power directly correlates with robot motion.
        Pn = Vn * In --> P / (MV * MI) = (V / MV) * (I / MI) --> Normalized Power = Normalized Voltage * Normalized Current
            MV = Max Voltage
            MI = Max Current
        Method 1:
            Let It represent the target current and Ia represent the actual current.
            It = Pn / Vn, where Pn is the target power and Vn is the current voltage
            Ia = In, where In is the current current
            Compare It and Ia.
        Method 2:
            Let Pt represent the target power and Pa represent the actual power.
            Pt = Pn, where Pn is the target power
            Pa = Vn * In, where Vn is the current voltage and In is the current current
        */

        // Method 1
        // double targetCurrent = power / (voltage / MAX_VOLTAGE);
        // double actualCurrent = filter.out(motor.getCurrent(CurrentUnit.AMPS)) / maxCurrent;
        // return power + pidf.out(targetCurrent - actualCurrent);

        // Method 2
        double actualPower = (voltage / MAX_VOLTAGE) * (filter.out(motor.getCurrent(CurrentUnit.AMPS)) / maxCurrent);
        return power + pidf.out(power - actualPower);
    }

    public Filter.LowPass getFilter() {
        return filter;
    }

    public static double getVoltage() {
        double ret = MAX_VOLTAGE;
        for (VoltageSensor sensor : Mollusc.instance().hardwareMap.voltageSensor) {
            double voltage = sensor.getVoltage();
            if (voltage > 0 && voltage < ret) {
                ret = voltage;
            }
        }
        return ret;
    }
}

/*
Copyright 2024 Trobotix 8696

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
