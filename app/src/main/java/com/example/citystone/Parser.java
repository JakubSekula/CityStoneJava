package com.example.citystone;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Parser {

    static boolean pkey = true;

    public static void main(String[] args ){

    }

    private static String changeOccurances( String sentence, String what, String to ){
        return sentence.replace( what, to );
    }

    private static HashMap<String,String> createMap( List<String> list ){
        HashMap<String,String> inner = new HashMap<String,String>();

        for( String mess : list ){

            mess = changeOccurances( mess, "\"", "" );

            String[] split = mess.split(":");

            inner.put( split[ 0 ], split[ 1 ] );
        }

        return inner;
    }

    private static HashMap<String, HashMap<String,String>> returnMap( List<String> array ){

        List<String> list = new ArrayList<String>();

        HashMap<String, String> help = new HashMap<String, String>();
        HashMap<String, HashMap<String, String>> outer = new HashMap<String, HashMap<String, String>>();

        for( String message : array ){
            list = returnParsed( message, "," );
            help = createMap( list );
            String[] helpf;
            if( pkey == true ) {
                helpf = changeOccurances(list.get(2), "\"", "").split(":");
            } else {
                helpf = changeOccurances(list.get(0), "\"", "").split(":");
            }
            outer.put( helpf[ 1 ], help );
        }

        return outer;

    }

    private static String returnTrimmed( String json ){

        json = json.substring( 1 );
        json = json.substring( 0, json.length() - 1 );

        return json;
    }

    public static HashMap<String, HashMap<String,String>> parseJsonData(String jsonStrings) {
        HashMap<String, HashMap<String,String>> Hash = new HashMap<String, HashMap<String,String>>();

        if( jsonStrings != null && jsonStrings.indexOf( "[{" ) != -1 ) {
            Hash = Parser.FromJSON( jsonStrings );
        } else {
            HashMap<String, String> notFound = new HashMap<String, String>();
            notFound.put( "cobjednavky", "Nenalezeny žádné záznamy" );
            Hash.put( "0", notFound );
        }

        return Hash;
    }

    private static List<String> returnParsed( String json, String pattern ){

        List<String> array = new ArrayList<String>();
        int i = 0;

        while( json.indexOf( pattern ) != -1 ) {
            int idx = json.indexOf( pattern );
            array.add( i, returnTrimmed( json.substring( 0, idx + 1 ) ) );
            json = json.substring( idx + 1 );
            i++;
        }

        array.add( i, returnTrimmed( json ) );

        return array;

    }

    public static HashMap<String, HashMap<String,String>> FromJSON( String json ){

        HashMap<String, HashMap<String,String>> fin = new HashMap<String, HashMap<String,String>>();

        json = returnTrimmed( json );

        List<String> array = new ArrayList<String>();

        array = returnParsed( json, "},{" );

        fin = returnMap( array );

        return fin;
    }

}
