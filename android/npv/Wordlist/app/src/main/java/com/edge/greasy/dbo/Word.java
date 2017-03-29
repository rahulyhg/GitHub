package com.edge.greasy.dbo;

public class Word {
	public int _id;
	public String _word;
	public String _meaning = "";
	public String _sentence = "";
	public String _category = "";
	public String _status = "";
	public String _marathimeaning = "";
	public String _hindiMeaning = "";

	public Word(int id, String word, String meaning, String sentence, String category, String status, String marathimeaning, String hindiMeaning) {
		this._id = id;
		this._word = word;
		this._meaning =  meaning;
		this._sentence = sentence;
		this._category  =  category;
		this._status = status;
		this._marathimeaning = marathimeaning;
		this._hindiMeaning = hindiMeaning;
	}

	public Word() {
		// TODO Auto-generated constructor stub
	}

	public String toRead(){
		StringBuffer sb = new StringBuffer();
		sb.append(" Next Word is " + _word + ". ");
		sb.append(getSpelling());
		sb.append( _word +" means " + _meaning + ". ");
        if (_sentence == null){
            sb.append(" No Sentence available. ");
        }else{
            sb.append(" Reading Sentence. " + _sentence + ". ");
        }
        sb.append(" So " + _word + " " +getSpelling() + " means " + _meaning + ". ");
		return sb.toString();
	}

    private String getSpelling() {
        StringBuffer _spelling = new StringBuffer(" .");
        for(char c: _word.toCharArray()){
            _spelling.append(c + ",");
        }
		_spelling.append(" ");
        return _spelling.toString();
    }

    public int get_id() {
		return _id;
	}


	public void set_id(int _id) {
		this._id = _id;
	}


	public String get_word() {
		return _word;
	}


	public void set_word(String _word) {
		this._word = _word;
	}


	public String get_meaning() {
		return _meaning;
	}


	public void set_meaning(String _meaning) {
		this._meaning = _meaning;
	}


	public String get_sentence() {
		return _sentence;
	}


	public void set_sentence(String _sentence) {
		this._sentence = _sentence;
	}

	public String get_category() {
		return _category;
	}

	public void set_category(String _category) {
		this._category = _category;
	}

	public String get_status() {
		return _status;
	}

	public void set_status(String _status) {
		this._status = _status;
	}

	public String get_marathimeaning() {
		return _marathimeaning;
	}

	public void set_marathimeaning(String _marathimeaning) {
		this._marathimeaning = _marathimeaning;
	}

	public String get_hindiMeaning() {
		return _hindiMeaning;
	}

	public void set_hindiMeaning(String _hindiMeaning) {
		this._hindiMeaning = _hindiMeaning;
	}
	
}
