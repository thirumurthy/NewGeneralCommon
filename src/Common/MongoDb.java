package Common;
 
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.BSONObject;
import org.bson.types.ObjectId;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;

public class MongoDb {
	
	public static MongoClient mongoClient=null;
public static MongoClient  getMongoDbConnection(String config) throws Exception { 
	  
		if(mongoClient!=null) return mongoClient;
		mongoClient=null;
		try{
			JsonObject jobj=JSON.strToJson( DocumentReader.AppData.get(config)); 
			 // To connect to mongodb server
			// mongodb://username:password@localhost:2511 
			  mongoClient = new MongoClient(new MongoClientURI(jobj.get("url").getAsString())); 
		}
		catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			
			
		}
	     
	    return mongoClient;
	  }


	public static String  saveToDB(String config,String Collection,String data ,boolean isdefault)
	{
		String resp="";
		try
		{
			DBCollection coll =  getCollection(  config,  Collection);
			 
			DBObject dbObject = (DBObject)com.mongodb.util.JSON.parse(data);
			coll.insert(dbObject);
			DBCursor jsonDoc = coll.find();
			  while (jsonDoc.hasNext()) {
				  resp+=jsonDoc.next();
			  }
		}
		catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			
		}
		return resp;
	}

	public static String getbyMultiFilter(String config,String Collection,HashMap<String, String> filter)
	{
		String resp="";
		DBCursor cursor = null;
		try
		{
			DBCollection coll =  getCollection(  config,  Collection);
			 
			   
			  //   Get documents of a collection by adding mutliple clauses like name and age.
			  BasicDBObject andQuery = new BasicDBObject();
			  List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			  for (Map.Entry<String, String> entry : filter.entrySet()) {
				     
				    obj.add(new BasicDBObject( entry.getKey(), entry.getValue()));
				}
			   
			  andQuery.put("$and", obj);
			 
			  cursor = coll.find(andQuery);
			  while (cursor.hasNext()) {
				  resp+=cursor.next();
			  }
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return resp;
		
	}
	
	/**
	 * 
	 * @param config
	 * @param Collection
	 * @param andfilter  null to empty
	 * @param orfilter   null to empty
	 * @param operationfield  example: {"timestamp": { "$gte": 1412204098, "$lte": 1412204099 }}
	 * @param orderby  field name example: timestamp
	 * @param ordertype example asc or desc
	 * @param limit
	 * @param offset
	 * @return
	 */

	public static String FullSearch(String config,String Collection,HashMap<String, String> andfilter,HashMap<String, String> orfilter,String operationfield,  String orderby,String ordertype,int limit,int offset)
	{
		 
		JsonObject result=new JsonObject();
		DBCursor cursor = null;
		
		try
		{
			JsonArray respdata=new JsonArray();
			result.addProperty("Count", 0);
			result.add("Data",respdata);
			limit=limit==0?10:limit;
			limit=limit>100?100:limit; // performance improvement
			DBCollection coll =  getCollection(  config,  Collection);
			 
			   
			  //   Get documents of a collection by adding mutliple clauses like name and age.
			  BasicDBObject andQuery = new BasicDBObject();
			  
			  if(andfilter!=null&&!andfilter.isEmpty()&&andfilter.size()>0)
			  {
				  List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
				  for (Map.Entry<String, String> entry : andfilter.entrySet()) {
					     
					    obj.add(new BasicDBObject( entry.getKey(), entry.getValue()));
					}
				   
				  andQuery.put("$and", obj);
			  }
			  if(orfilter!=null&&!orfilter.isEmpty()&&orfilter.size()>0)
			  {
				  List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
				  for (Map.Entry<String, String> entry : orfilter.entrySet()) {
					     
					    obj.add(new BasicDBObject( entry.getKey(), entry.getValue()));
					}
				   
				  andQuery.put("$or", obj);
			  }
			  if(!General.isNullOrEmpty(operationfield))
			  {
				  BSONObject bson = (BSONObject)com.mongodb.util.JSON.parse(operationfield);
				  //operationfield 
				  andQuery.putAll(bson); 
			  }
			  
			  
			  
		
			  result.addProperty("Count", coll.find(andQuery).count());
			  if(result.get("Count").getAsInt()>0)
			  {
				  
				  if(!General.isNullOrEmpty(orderby))
				  {
					  if(!General.isNullOrEmpty(ordertype))
					  {
						  cursor = coll.find(andQuery).sort(new BasicDBObject(orderby,(ordertype.equals("asc")?1:-1))) .skip(offset).limit(limit) ;
						   
					  }
					  else
					  {
						  cursor = coll.find(andQuery).sort(new BasicDBObject(orderby,1)).skip(offset).limit(limit) ;
						  
					  }
				  }
				  else
					  cursor = coll.find(andQuery).skip(offset).limit(limit) ;
				  while (cursor.hasNext()) {
					  JsonObject jobj=JSON.deSerializeObject(cursor.next().toString());
					  respdata.add(jobj);
				  }
			  }
			  result.add("Data", respdata);
		}
		catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
		return result.toString();
		
	}
	
	
	public static String QuerySearch(String config,String Collection,String query)
	{
		 
		JsonArray respdata=new JsonArray();
		DBCursor cursor = null;
		
		try
		{ 
			
			DBCollection coll =  getCollection(  config,  Collection); 
			  BasicDBObject andQuery = (BasicDBObject) JSON.deserialize(query, BasicDBObject.class.getName()) ;
			   cursor = coll.find(andQuery) ;
				  while (cursor.hasNext()) {
					  JsonObject jobj=JSON.deSerializeObject(cursor.next().toString());
					  respdata.add(jobj);
				  } 
		}
		catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
		return respdata.toString();
		
	}
	public static String QuerySearch(String config,String Collection,String query,int limit,int offset)
	{
		 
		JsonArray respdata=new JsonArray();
		DBCursor cursor = null;

		JsonObject result=new JsonObject();
		try
		{   
			result.addProperty("Count", 0);
			result.add("Data",respdata);
			limit=limit==0?10:limit;
			limit=limit>100?100:limit; // performance improvement
			DBCollection coll =  getCollection(  config,  Collection); 
			  BasicDBObject andQuery = (BasicDBObject) JSON.deserialize(query, BasicDBObject.class.getName()) ;
			   cursor = coll.find(andQuery).skip(offset).limit(limit) ; 
			   result.addProperty("Count", coll.find(andQuery).count());
				  if(result.get("Count").getAsInt()>0)
				  {
					  while (cursor.hasNext()) {
						  JsonObject jobj=JSON.deSerializeObject(cursor.next().toString());
						  respdata.add(jobj);
					  } 
					  result.add("Data", respdata);
				  }
		}
		catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
		return result.toString();
		
	}
	
	/**
	 * Update fields and push item into array. Leave empty to leave the fields.
	 * @param config
	 * @param Collection
	 * @param id
	 * @param pushitem
	 * @param updateitem
	 * @return
	 */
	
	public static String updateMultiField(String config,String Collection,String id,String pushitem,String updateitem)
	{
		String resp="";
		try{
			DBCollection coll =  getCollection(  config,  Collection);
			BasicDBObject searchQuery = new BasicDBObject("_id", id);
			 DBObject dbObject =null;
			 
			Object obj = com.mongodb.util.JSON.parse(updateitem);
			BasicDBObject updateFields=(BasicDBObject) obj;
			 
			if(pushitem!=null&&!pushitem.equals("")&&pushitem.trim().length()>0)
			   dbObject = (DBObject)com.mongodb.util.JSON.parse(pushitem);
			 
			BasicDBObject setQuery = new BasicDBObject();
			if(updateFields!=null)
			setQuery.append("$set", updateFields);
			if(dbObject!=null)
			setQuery.append("$push", dbObject);
			 
				WriteResult wr =coll.update(searchQuery, setQuery, false, true);
				resp=wr.toString();
			 
			
		}
		catch (Exception exp) {
			resp=exp.toString();
		}
		return resp;
	}
	

	/**
	 * It will get the result using one field search
	 * 
	 *   r fields pass field name. for multiple object use dot example : address.area = Aundh.
	 * @param pass the mogodb config address from app.config
	 * @param Collection
	 * @param field  
	 * @author Thirumurthy
	 * @param value
	 * @return
	 */
	public static String getbyfield(String config,String Collection,String field,String value)
	{
		String resp="";
		DBCursor cursor = null;
		List<String> lst=new ArrayList<String>();
		try
		{
			 
			DBCollection coll =  getCollection(  config,  Collection);
			   
			//  Get documents of a collections where age = 25.
			  BasicDBObject whereQuery = new BasicDBObject();
			  whereQuery.put(field, value);
			  cursor = coll.find(whereQuery);
			  while(cursor.hasNext()) {
				  lst.add( cursor.next().toString());
				  
			  }
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return lst.toString();
		
	}
	
	public static String getbyId(String config,String Collection,String id)
	{
		String resp="";
		DBCursor cursor = null;
		try
		{
			 
			DBCollection coll =  getCollection(  config,  Collection);
			   
			//  Get documents of a collections where age = 25.
			BasicDBObject query = new BasicDBObject();
		    query.put("_id", new ObjectId(id.getBytes()));  
			  cursor = coll.find(query);
			  while(cursor.hasNext()) {
				  resp+=cursor.next();
			  }
		}
		catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			
		}
		return resp;
		
	}
	
	public static String toHex(String arg) {
	    return String.format("%040x", new BigInteger(1, arg.getBytes(Charset.defaultCharset())));
	}
	
	
	public static String appendToArray(String config,String Collection,String id,String field,String value)
	{
		String resp="";
		try
		{
			DBCollection coll =  getCollection(  config,  Collection);
			DBObject dbObject = (DBObject)com.mongodb.util.JSON.parse(value);
			BasicDBObject updateQuery = new BasicDBObject("_id", id);
			BasicDBObject updateCommand = new BasicDBObject("$push", new BasicDBObject(field, dbObject));
			coll.update(updateQuery, updateCommand);
			resp=coll.findOne().toString();

		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return resp;
	}
	
	
	// ///////////////////// Private methods
	private static DBCollection getCollection(String config,String Collection)
	{
		DBCollection coll =null;
		try
		{
			JsonObject jobj=JSON.strToJson( DocumentReader.AppData.get(config)); 
			MongoClient mclient=getMongoDbConnection(config);
			@SuppressWarnings("deprecation")
			DB db = mclient.getDB( jobj.get("database").getAsString() );
			  coll = db.getCollection(Collection);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return coll ;
	}
	
	
}
