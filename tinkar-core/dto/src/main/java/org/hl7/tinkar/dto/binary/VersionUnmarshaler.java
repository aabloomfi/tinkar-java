package org.hl7.tinkar.dto.binary;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 *  Used to indicate which static method on a class shall be used as the
 *  Version Unmarshaler.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface VersionUnmarshaler {

}
