/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

@SuppressWarnings("module") // 7 in HL7 is not a version reference
module org.hl7.tinkar.collection {
    requires java.base;
    requires transitive org.hl7.tinkar.common;
    exports org.hl7.tinkar.collection;
    exports org.hl7.tinkar.collection.store;
}

