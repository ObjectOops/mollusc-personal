package org.firstinspires.ftc.teamcode.mollusc.utility;

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.HashMap;

public class Controls {

    private static HashMap<String, Boolean> singlePressMarkers = new HashMap<>();
    private static HashMap<String, ElapsedTime> holdMarkers    = new HashMap<>();

    // Squares `value` while retaining sign. Useful for more natural joystick feel.
    public static double quadratic(double value) {
        return value * Math.abs(value);
    }
    
    public static boolean singlePress(String marker, boolean value) {
        Boolean previous = singlePressMarkers.get(marker);
        if (previous == null) {
            previous = false;
        }
        singlePressMarkers.put(marker, value);
        if (!previous && value) {
            return true;
        }
        return false;
    }

    // Returns true on held value after a specified duration (seconds), false otherwise.
    public static boolean spacedHold(String marker, boolean value, double duration) {
        ElapsedTime time = holdMarkers.get(marker);
        if (time == null) {
            time = new ElapsedTime();
        }
        if (time.seconds() < duration) {
            return false;
        }
        time.reset();
        return true;
    }
}

/*
Copyright 2023 Trobotix 8696

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
