/*
 * Copyright [2018] [Eric Xu]
 *
 *     Author: Eric Xu
 *     Email: eric.xu@aukletapm.com
 *     WebURL: https://github.com/aukletapm
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aukletapm.go.sample.springboot.controller;

import com.aukletapm.go.servlet.AukletGoHttpServletHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eric Xu
 * @date 01/03/2018
 */
@Controller
@EnableAutoConfiguration
public class SampleController {

    private AukletGoHttpServletHandler aukletGoHttpServletHandler;

    @Autowired
    public SampleController(AukletGoHttpServletHandler aukletGoHttpServletHandler) {
        this.aukletGoHttpServletHandler = aukletGoHttpServletHandler;
    }

    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8100", "http://alert.go.aukletapm.com"})
    @RequestMapping("/auklet-go")
    public String akuletGoEndpoint(HttpServletRequest request, HttpServletResponse response) {
        aukletGoHttpServletHandler.handle(request, response);
        return null;
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/auklet-go";
    }

}
