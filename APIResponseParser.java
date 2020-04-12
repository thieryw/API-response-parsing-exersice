import java.util.Arrays;
public class APIResponseParser {
    
     /**
	 * Parses the input text and returns a Book instance containing
	 * the parsed data. 
	 * @param response text to be parsed
	 * @return Book instance containing parsed data
	 */
     public static Book parse(String response) {
        Book book = new Book();
        String endRule = "<";
        
        String[] startRules = {"<title>", "<name>", "<original_publication_year type=\"integer\">",
                                "<average_rating>", "<ratings_count type=\"integer\">",
                                "<image_url>"};

        
        String[] bookAttributes = parse(response, startRules, endRule);

        System.out.println(Arrays.toString(bookAttributes));
                                
        String startRule;
        
        for(int i = 0, rulesLength = startRules.length; i < rulesLength; i++){
            startRule = startRules[i];
            switch(startRule){

                case "<original_publication_year type=\"integer\">" : 
                book.setPublicationYear(Integer.parseInt(bookAttributes[i])); 
                break;

                case "<average_rating>" :
                book.setAverageRating(Double.parseDouble(bookAttributes[i]));
                break;

                case "<ratings_count type=\"integer\">" : 
                String[] ratingsCountParts = bookAttributes[i].split(",");
                bookAttributes[i] = "";
                for (String string : ratingsCountParts) {
                    bookAttributes[i] += string;
                }
                book.setRatingsCount(Integer.parseInt(bookAttributes[i]));
                break;

                case "<title>" : 
                book.setTitle(bookAttributes[i]);
                break;

                case "<name>" :
                book.setAuthor(bookAttributes[i]);
                break;

                case "<image_url>" : 
                book.setImageUrl(bookAttributes[i]);
                break;

            }

        }
            

		return book;
     }


     private static String parse(String response, String startRule, String endRule){

         int indexOfStartRule = response.indexOf(startRule);

         String partAfterStartRule = response.substring(indexOfStartRule);

         int indexOfEndRule = partAfterStartRule.indexOf(endRule + "/");

         String SubstringToReturn = partAfterStartRule.substring(startRule.length(), indexOfEndRule);

         return SubstringToReturn;

     }

     private static String[] parse(String response, String[] startRules, String endRule){

         String[] bookAttributes = new String[startRules.length];

         for(int i = 0, rulesLength = startRules.length; i < rulesLength; i++){
             bookAttributes[i] = parse(response, startRules[i], endRule);
         }

         return bookAttributes;
     }
     
     
     public static void main(String[] args) {
		String response = "<work>" + 
	                            "<id type=\"integer\">2361393</id>" +
	                            "<books_count type=\"integer\">813</books_count>" +
	                            "<ratings_count type=\"integer\">1,16,315</ratings_count>" + 
	                            "<text_reviews_count type=\"integer\">3439</text_reviews_count>" +
	                            "<original_publication_year type=\"integer\">1854</original_publication_year>" +
								"<original_publication_month type=\"integer\" nil=\"true\"/>" +
								"<original_publication_day type=\"integer\" nil=\"true\"/>" +
								"<average_rating>3.79</average_rating>" +
								"<best_book type=\"Book\">" +
									"<id type=\"integer\">16902</id>" +
									"<title>Walden</title>" + 
									"<author>" +
										"<id type=\"integer\">10264</id>" + 
										"<name>Henry David Thoreau</name>" + 
									"</author>" +
									"<image_url>" + 
										"http://images.gr-assets.com/books/1465675526m/16902.jpg" +
									"</image_url>" +
									"<small_image_url>" + 
										"http://images.gr-assets.com/books/1465675526s/16902.jpg" +
									"</small_image_url>" +
								"</best_book>" +
							"</work>";
        
        Book myBook = new Book();

        myBook = parse(response);
                            
        System.out.println(myBook.getAuthor() + 
        "\n" + myBook.getTitle() + 
        "\n" + myBook.getPublicationYear() +
        "\n" + myBook.getAverageRating() +
        "\n" + myBook.getRatingsCount() +
        "\n" + myBook.getImageUrl());
	}
}