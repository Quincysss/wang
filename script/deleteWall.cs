using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class deleteWall : MonoBehaviour
{
    public int time;
    public float times;
    public GameObject player;

    void Update()
    {
        if (transform.name.Contains("Clone"))
        {
            if (player.transform.position.z > transform.position.z)
            {
                StartCoroutine(stop());
            }
        }
    }

    IEnumerator stop()
    {
        yield return new WaitForSeconds(time);
        Destroy(gameObject);
    }
}
