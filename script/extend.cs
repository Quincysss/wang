using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class extend : MonoBehaviour
{
    float timer;
    float starttime;
    public GameObject floor;
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
    Vector3 position;

    void Start()
    {
        position = floor.transform.position;
        starttime = 2;
    }
    void Update()
    {
        timer += Time.deltaTime;
        if(timer >= starttime)
       {
            copy();
            timer = 0;
       }
    }
    private void copy()
    {
        position += new Vector3(0, 0, length * 2);
        Instantiate(floor, position, floor.transform.rotation);
        obstacle();
    }

    private void obstacle()
    {
        int b = Random.Range(min, max + 1);
        float q = (length * 2) / b;
        for (int i = 0; i < b; i++)
        {
            int a = Random.Range(0, items.Length);
            float zMin = position.z - length;
            float xNumber = Random.Range(-width, width);
            Vector3 pos = new Vector3(xNumber, 0, zMin + q * i);
            var rota = items[a].transform.rotation;
            rota = new Quaternion(0, 180, 0, 0);
            items[a].transform.localScale = new Vector3(objectX, objectY, objectZ);
            Instantiate(items[a], pos, rota);
        }
    }

    public void setTime(float time)
    {
        starttime = time;
    }
}
