using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class show : MonoBehaviour
{
    public Text message;
    public void add(string color)
    {
        message.text = "3 " + color + ": get 5 seconds bonus";
        StartCoroutine(end());
    }

    IEnumerator end()
    {
        yield return new WaitForSeconds(2);
        message.text = "";
    }
}
