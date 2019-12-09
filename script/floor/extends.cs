using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class extends : MonoBehaviour
{
    public Collider bound;
    public GameObject[] items;
    public float width;
    public float length;
    public int min;
    public int max;
    public int time;
    public float objectX;
    public float objectY;
    public float objectZ;
    private string[] tags = {"Player","RED","BLUE","YELLOW"};

    public void OnTriggerEnter(Collider collider)
    {
        foreach (string p in tags)
        {
            if (collider.tag == p)
            {
                if (gameObject.name.Contains("Clone"))
                {
                    StartCoroutine(stop());
                }
            }
        }
    }

    IEnumerator stop()
    {
        yield return new WaitForSeconds(time);
        Destroy(gameObject);
    }
}
