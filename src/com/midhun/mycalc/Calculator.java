package com.midhun.mycalc;

import java.util.ArrayList;

import expr.Expr;
import expr.Parser;
import expr.SyntaxException;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData.Item;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("NewApi")
public class Calculator extends Activity implements OnClickListener {
	GridView gridView;
	TextView calculatorScreen;
	CustomGridViewAdapter customGridAdapter;
	ArrayList<Item> gridArray = new ArrayList<Item>();   
	TextView t;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);
		gridView = (GridView) findViewById(R.id.gridview);
		calculatorScreen = (TextView) findViewById(R.id.textView1);
		setGridContent();
		bindGetValueFromGrid();
		t = (TextView)findViewById(R.id.textView1);
	    t.setOnClickListener(this);
		
	}
	
	public void onClick(View arg0) {
	    t.setText("");  
	}

	private void bindGetValueFromGrid() {
		gridView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	        	Item item = (Item) parent.getItemAtPosition(position);
	        	if(item.getText() != "="){
	        		setCalculatorScreen(item.getText());
	        	}else{
					setCalculatedValue();
	        	}
	        }
	    });
	}

	protected void setCalculatedValue(){
		 String expression = calculatorScreen.getText().toString();
		 String code = expression;
		 double result = 0;
		try {
		     Expr expr = Parser.parse(code);
		     result = expr.value();
		 } catch (SyntaxException e) {
		     e.printStackTrace();
		 }
		calculatorScreen.append(" = " + result);
		System.out.println(String.format("Result: %.04f", result));
	}

	public void setCalculatorScreen(CharSequence text) {
		calculatorScreen.append(text);
	}

	private void setGridContent() {
		String[] values = {"9", "8", "7", "*", "6", "5" , "4", "/", "3", "2", "1" , "+" , "0", ".", "=", "-"};
		for (String string : values) {
			gridArray.add(new Item(string));
		}
		customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
		gridView.setAdapter(customGridAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.calculator, menu);
		return true;
	}

}
