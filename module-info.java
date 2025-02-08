/**
 * Provides the <strong>cs1302-gallery</strong> application.
 */
module ticketmaster {
    requires transitive java.logging;
    requires transitive java.net.http;
    requires transitive javafx.controls;
    requires transitive com.google.gson;
    opens ticketmaster;
} // module
