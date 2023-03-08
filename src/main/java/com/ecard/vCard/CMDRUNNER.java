// package com.ecard.vCard;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import com.ecard.vCard.Entity.Person;
// import com.ecard.vCard.Repository.PersonRepository;

// @Component
// public class CMDRUNNER implements CommandLineRunner {

//     private static final Logger logger = LoggerFactory.getLogger(CMDRUNNER.class);

//     @Autowired
//     private PersonRepository personRepository;

//     @Override
//     public void run(String... args) throws Exception {
//         Person per = new Person(1, "Panji Maulana", "IT Application", "Panji@Gmail.com", "08123456789", null);
//             personRepository.save(per);
//         logger.info("Has been created");
//     }
    
// }
