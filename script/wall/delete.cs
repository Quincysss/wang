using System.Collections;
using UnityEngine;

public class delete : MonoBehaviour
{
    public float time;
    public deleteWall wall;
    public ParticleSystem smoke;

    private void Start()
    {
        smoke.Stop();
    }
    void Update()
    {
        if (gameObject.name == "delete")
        {
            StartCoroutine(stop());
        }
        float check = transform.position.z - PlayerPrefs.GetFloat("position");
        if (check < 50 && check > 0 && transform.position.z > 0)
        {
            StartCoroutine(stop());
        }
    }

    IEnumerator stop()
    {
        gameObject.SetActive(false);
        smoke.Play();
        yield return new WaitForSeconds(time);
        smoke.Stop();
        Destroy(gameObject);
    }
}
