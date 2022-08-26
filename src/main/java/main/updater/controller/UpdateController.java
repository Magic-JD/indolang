package main.updater.controller;

import main.updater.UpdateFromFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/update")
public class UpdateController {

   @Autowired
   UpdateFromFile updateFromFile;

//    @ResponseBody
//    @PutMapping("")
//    public void updateFromFile() {
//        updateFromFile.update();
//    }

}
