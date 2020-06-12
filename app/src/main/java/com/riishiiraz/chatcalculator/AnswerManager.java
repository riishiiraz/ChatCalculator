package com.riishiiraz.chatcalculator;


import android.widget.Toast;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class AnswerManager {



    public static String eval(String eqn)
    {
        eqn = eqn.replace('×','*').replace('÷','/');

        ScriptEngineManager scriptEngineManager= new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("js");


        String ans="";

        try
        {
            ans = scriptEngine.eval(eqn).toString();
        }
        catch (Exception e)
        {
            ans = "Syntax Error";
        }

        return ans;
    }

    public static void sendAnswer(Messege msg)
    {

        String ans = eval(msg.getText());
        String time = msg.getTime();

        Messege AnsObj = new Messege(ans , "Computer",time);

        MainActivity.db.addMessege(AnsObj);
        MainActivity.Messeges.add(AnsObj);

        int pos = MainActivity.Messeges.size()-1;
        MainActivity.messegeAdapter.notifyItemInserted(pos);
        MainActivity.RCV.scrollToPosition(pos);

    }
}
