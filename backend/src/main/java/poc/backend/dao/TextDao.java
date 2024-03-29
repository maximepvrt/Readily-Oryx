package poc.backend.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import poc.backend.dto.TextDto;
import poc.backend.entity.Text;

public class TextDao {

	static private GenericDAO<Text> delegate = new GenericDAO<Text>("text", Text.class){

		public void initialize() throws Exception{
			ensureIndex("accountId", SortOrder.ASCENDING);
		}
	};
	
	public static boolean saveOrUpdate(TextDto textDto, String login) {
		Text text = new Text();
//		if (text.id == null) {
//			text.creationDate = new Date();
//		} else {
		text.modificationDate = new Date();
//		}
		text.title = textDto.title;
		text.summary = textDto.summary;
		text.category = textDto.category;
		text.type = textDto.type;
		text.mood = textDto.mood;
		text.content = textDto.content;
		text.login = login;
		text.timeToRead = Math.max(((textDto.content.split(" ").length) / 200),1);;
		return delegate.saveOrUpdate(text);
	}

	public static List<Text> search(long timeToReadMin, long timeToReadMax, String type, String category, int offset, int count) {

		GenericDAO.Configuration config = new GenericDAO.Configuration().setSkip(offset).setLimit(count).setSortQuery("{creationDate: -1}");
        String query = "{timeToRead:{$gte:#},timeToRead:{$lte:#}";
        List<Object> arguments = new ArrayList<>();
        arguments.add(timeToReadMin);
        arguments.add(timeToReadMax);
        
        if (category != null) {
        	query += ", category: #";
            arguments.add(category);
        }
        
        query += "}";

        Object[] args = new Object[arguments.size()];
        arguments.toArray(args);
        
        return delegate.find(config, query, args);
	}
	
	public static Text get(String id){
		return delegate.get(id);
	}
	
	
	
}
