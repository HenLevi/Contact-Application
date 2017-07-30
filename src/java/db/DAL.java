package db;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import model.*;

/**
 *
 * @author chen
 */
public class DAL {

    public static String mySqlConnectionString = "jdbc:mysql://localhost/list?user=root&password=1";

    /**
     * Get all contact from database as Json array
     *
     * @return Json array that contains all the contact in database
     */
    public static JsonArray allContactList() {
        String query = "Select * FROM `contactlist`";
        DataBase db = DataBase.getInstance();
        JsonArray rslt = db.getRSasJsonArray(query);
        return rslt;
    }

    /**
     * Get contact by a given id
     *
     * @param id
     * @return contact object (with a given id), of null if contact with given
     * id was not found
     */
    public static contact getContactById(String id) {
        contact output = null;
        String query = "Select * FROM `contactlist` WHERE `id` = '" + id + "'";
        DataBase db = DataBase.getInstance();
        JsonArray rslt = db.getRSasJsonArray(query);
        if (rslt.size() > 0) {
            JsonObject o = (JsonObject) rslt.get(0);
            output = new contact(o.get("id").getAsString(), o.get("name").getAsString(), o.get("job").getAsString(),o.get("img").getAsString());
        }
        return output;
    }

    /**
     * Delete contact by a given id
     *
     * @param id
     * @return number of rows deleted or -1 if fails.
     */
    public static String deleteContactById(String id) {
        String output;
        String query = "DELETE FROM `contactlist` WHERE `id` = '" + id + "'";
        DataBase db = DataBase.getInstance();
        output = String.valueOf(db.Delete(query));
        return output;
    }

    /**
     * get contact by id
     *
     * @param c-contact
     * @return number of rows updated or -1 if fails.
     */

    public static String updateContact(contact c) {
        String output;
        String query = "UPDATE `contactlist` SET "
                + "`name`= '" + c.getName() + "' ,"
                + "`job`= '" + c.getJob() + "' " 
                + "WHERE `id` = '" + c.getId() + "'";
        DataBase db = DataBase.getInstance();
        output = String.valueOf(db.Update(query));
        return output;
    }

    /**
     * get contact by id
     *
     * @param id of contact
     * @return the contact
     */

    public static JsonObject getcontactById(String id) {
        String query = "SELECT * FROM `contactlist` WHERE `id` = '" + id + "'";
        DataBase db = DataBase.getInstance();
        JsonArray rsltArr = db.getRSasJsonArray(query);
        JsonObject rslt;
        if (rsltArr.size() > 0) {
            rslt = rsltArr.get(0).getAsJsonObject();
        } else {
            rslt = new JsonObject();
        }
        return rslt;
    }

    /**
     * Insert new item into the DataBase
     *
     * @param c - contact
     * @return New ID
     */
    public static String insert(contact c) {
        String output;
//        String query = "INSERT INTO `contactlist` ( `name`, `job`) "
//                + "VALUES ( '" + c.getName() + "', '" + c.getJob() + "')";
        
                String query = "INSERT INTO `contactlist` ( `name`, `job` ) "
                + "VALUES ( '" + c.getName() + "', '" + c.getJob() + "')";
        
        DataBase db = DataBase.getInstance();
        output = String.valueOf(db.Insert(query));
        c.setId(output);
        return output;
    }

}
