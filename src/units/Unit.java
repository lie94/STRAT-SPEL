package units;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public abstract class Unit {
	protected String name;
	protected boolean isMale;
	protected Unit(){
		if(new Random().nextBoolean())
			isMale = true;
		else
			isMale = false;
		name = generateName();
	}
	private String generateName(){
		Random rn = new Random();
		StringBuilder s = new StringBuilder();
		List<String> temp;
		boolean prefix = rn.nextBoolean();
		if(prefix){
			try {
				temp = Files.readAllLines(Paths.get("src/res/text/prefix.txt"), StandardCharsets.UTF_8);
				s.append(temp.get(rn.nextInt(temp.size())));
			} catch (IOException e) {
				s.append("Nullis");
				e.printStackTrace();
			}
			s.append(" ");
		}
		if(isMale){
			try {
				temp = Files.readAllLines(Paths.get("src/res/text/male_names.txt"), StandardCharsets.UTF_8);
				s.append(temp.get(rn.nextInt(temp.size())));
			} catch (IOException e) {
				s.append("Nullis");
				e.printStackTrace();
			}
		}else{
			try {
				temp = Files.readAllLines(Paths.get("src/res/text/female_names.txt"), StandardCharsets.UTF_8);
				s.append(temp.get(rn.nextInt(temp.size())));
			} catch (IOException e) {
				s.append("Nullis");
				e.printStackTrace();
			}
		}
		if(!prefix){
			s.append(" ");
			try {
				temp = Files.readAllLines(Paths.get("src/res/text/postfix.txt"), StandardCharsets.UTF_8);
				s.append(temp.get(rn.nextInt(temp.size())));
			} catch (IOException e) {
				s.append("Nullis");
				e.printStackTrace();
			}
		}
		return s.toString();
	}
}
